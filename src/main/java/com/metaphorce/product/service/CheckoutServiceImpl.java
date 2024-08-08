package com.metaphorce.product.service;

import com.metaphorce.commonslib.dto.ProcessCheckoutRequest;
import com.metaphorce.commonslib.dto.ProcessCheckoutResponse;
import com.metaphorce.commonslib.dto.ProductDto;
import com.metaphorce.commonslib.entities.*;
import com.metaphorce.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private CardRepository cardRepository;


    @Override
    public void processCheckout(ProcessCheckoutRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (checkoutRepository.existsByOrder(order)) {
            throw new RuntimeException("Checkout already exists");
        }

        Cart cart = order.getCart();

        List<CartItem> items = cart.getItems();

        for (CartItem item : items) {

            if (item.getQuantity() > item.getProduct().getStock()) {
                throw new RuntimeException("Insufficient stock");
            }

            item.getProduct().setStock(item.getProduct().getStock() - item.getQuantity());
            productRepository.save(item.getProduct());

        }

        Users user = cart.getUser();
        user.setPhone(request.getPhoneUser());
        userRepository.save(user);

        Optional<List<UserLocation>> locations = userLocationRepository.findByUserId(user.getId());
        UserLocation userLocation = getUserLocation(request, locations, user);
        userLocationRepository.save(userLocation);

        PaymentMethod paymentMethod = paymentMethodRepository.getReferenceById(request.getPaymentMethodId());

        Optional<List<Card>> listCards = cardRepository.findByUserId(cart.getUser().getId());
        Card newCardUSer = getCardUser(request, listCards, user);

        Checkout checkout = new Checkout();
        checkout.setOrder(order);
        checkout.setTotalPaid(order.getTotalToPay());
        checkout.setStatus(Checkout.CheckoutStatusEnum.IN_PROGRESS);
        checkout.setDate(LocalDateTime.now());
        checkout.setCardUser(newCardUSer);
        checkout.setPaymentMethod(paymentMethod);
        checkoutRepository.save(checkout);

        Order updatedOrder = orderRepository.getReferenceById(order.getId());
        updatedOrder.setOrderStatus(Order.OrderStatusEnum.WAITING);
        orderRepository.save(updatedOrder);

    }

    private Card getCardUser(ProcessCheckoutRequest request, Optional<List<Card>> listCards, Users user) {
        Card newCardUSer = new Card();

        if (listCards.isPresent()) {
            for (Card card : listCards.get()) {
                if (card.getCardNumber().equals(request.getCardUser().getCardNumber())) {
                    newCardUSer = card;
                    break;
                }
            }
        }
        if (newCardUSer.getId() == null) {
            Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId());

            newCardUSer.setCardNumber(request.getCardUser().getCardNumber());
            newCardUSer.setCvv(request.getCardUser().getCvv());
            newCardUSer.setExpiration(request.getCardUser().getExpiration());
            newCardUSer.setPaymentMethod(paymentMethod.get());
            newCardUSer.setUser(user);
            cardRepository.save(newCardUSer);
        }
        return newCardUSer;
    }

    private static UserLocation getUserLocation(ProcessCheckoutRequest request, Optional<List<UserLocation>> locations, Users user) {
        UserLocation userLocation = new UserLocation();

        if (locations.isPresent()) {

            for (UserLocation location : locations.get()) {

                if (location.getAddress().equals(request.getUserLocation().getAddress())
                        && location.getCity().equals(request.getUserLocation().getCity())
                        && location.getCountry().equals(request.getUserLocation().getCountry())
                        && location.getPostalCode().equals(request.getUserLocation().getPostalCode())
                        && location.getState().equals(request.getUserLocation().getState())) {

                    userLocation = location;
                    break;
                }
            }
        }

        if (userLocation.getIdLocation() == null) {
            userLocation.setUser(user);
            userLocation.setAddress(request.getUserLocation().getAddress());
            userLocation.setCity(request.getUserLocation().getCity());
            userLocation.setCountry(request.getUserLocation().getCountry());
            userLocation.setPostalCode(request.getUserLocation().getPostalCode());
            userLocation.setState(request.getUserLocation().getState());
        }

        return userLocation;
    }


    @Override
    public  List<ProcessCheckoutResponse> getOrderCheckout(Long id) {
        List<ProcessCheckoutResponse> checkoutResponses = new ArrayList<>();

         List<Checkout> checkouts = checkoutRepository.findAll(); //TO DO: implement get all checkout by user id

        //Optional<Checkout> checkout = checkoutRepository.findById(id);

        if (!checkouts.isEmpty()) {

            for (Checkout checkout : checkouts) {

                ProcessCheckoutResponse checkoutResponse = new ProcessCheckoutResponse();
                checkoutResponse.setTotalToPay(checkout.getTotalPaid());
                checkoutResponse.setDate(checkout.getDate());
                checkoutResponse.setIdCheckout(checkout.getId());
                checkoutResponse.setCheckoutStatus(String.valueOf(checkout.getStatus()));
                checkoutResponse.setOrderId(checkout.getOrder().getId());
                checkoutResponse.setCartId(checkout.getOrder().getCart().getId());
                checkoutResponse.setUserId(checkout.getOrder().getCart().getUser().getId());
                checkoutResponse.setProducts(checkout.getOrder().getCart().getItems().stream().map(cartItem -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setProductId(cartItem.getProduct().getId());
                    productDto.setProductPathImage(cartItem.getProduct().getPathImage());
                    productDto.setProductName(cartItem.getProduct().getName());
                    productDto.setProductPrice(cartItem.getProduct().getPrice());
                    productDto.setProductQuantity(cartItem.getQuantity());


                    return productDto;
                }).collect(Collectors.toList()));
                checkoutResponses.add(checkoutResponse);
            }

            return checkoutResponses;
        } else {
            throw new RuntimeException("Checkout not found");
        }
    }
}
