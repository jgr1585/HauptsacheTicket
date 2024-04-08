package at.fhv.teamh.hauptsach_ticket.backend.communication;

import at.fhv.teamh.hauptsach_ticket.backend.services.*;
import at.fhv.teamh.hauptsach_ticket.backend.services.remote.AuthLdapService;
import at.fhv.teamh.hauptsach_ticket.backend.services.remote.CustomerDBService;
import at.fhv.teamh.hauptsach_ticket.backend.services.remote.MessageService;
import at.fhv.teamh.hauptsach_ticket.library.communication.ApplicationClient;
import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClient;
import at.fhv.teamh.hauptsach_ticket.library.dto.*;
import at.fhv.teamh.hauptsach_ticket.library.enums.Permission;
import at.fhv.teamh.hauptsach_ticket.library.exception.AuthenticationFailedException;
import at.fhv.teamh.hauptsach_ticket.library.exception.NoPermissionException;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Stateful
@Remote(ApplicationClient.class)
public class ApplicationClientImpl implements ApplicationClient {

    @Inject
    private EventService eventService;
    @Inject
    private TicketCategoryService ticketCategoryService;
    @Inject
    private TicketService ticketService;
    @Inject
    private OrderService orderService;
    @Inject
    private PaymentService paymentService;

    private static final CustomerDBClient customerDBClient = CustomerDBService.INSTANCE;
    private final List<TicketDTO> mutableShoppingCart = new LinkedList<>();

    @EJB
    private AuthLdapService authLdap;

    @NotNull
    @Override
    public List<TicketDTO> getShoppingCart() {
        return mutableShoppingCart;
    }

    private LdapUserDTO ldapUser;

    @NotNull
    @Override
    public LdapUserDTO getLdapUser() {
        return ldapUser;
    }


    @Override
    public boolean authenticate(@NotNull String username, @NotNull String password) throws AuthenticationFailedException {
        try {
            ldapUser = authLdap.authenticateEmployee(username, password);
            return true;
        } catch (Exception e) {
            throw new AuthenticationFailedException();
        }
    }

    @NotNull
    @Override
    public List<EventDTO> getEvents(int eventNumber, int eventsPerPage) {
        return eventService.getEvents(eventNumber, eventsPerPage);
    }

    @NotNull
    @Override
    public List<OrderDTO> getOrdersByCustomerId(int customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @NotNull
    @Override
    public List<TicketCategoryDTO> getTicketsForEvent(@NotNull UUID eventId) {
        return ticketCategoryService.findTicketsFromEvent(eventId);
    }

    @Override
    public int totalEvents() {
        return eventService.totalEvents();
    }

    @NotNull
    @Override
    public List<EventDTO> searchEvents(@NotNull String searchString, String eventName, String genre, LocalDate date, int eventNumber, int eventsPerPage) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            return eventService.searchEvents(searchString, eventName, genre, date, eventNumber, eventsPerPage);
        } else {
            throw new NoPermissionException();
        }
    }

    @NotNull
    @Override
    // @RequiresRole(Permission.ADMIN)
    public OrderDTO searchOrderById(@NotNull UUID id) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            return orderService.searchOrderById(id);
        } else {
            throw new NoPermissionException();
        }
    }

    @Override
    public int totalSearchEvents(@NotNull String searchString, String eventName, String genre, LocalDate date) {
        return eventService.totalSearchEvents(searchString, eventName, genre, date);
    }

    @NotNull
    @Override
    public List<TicketDTO> addTicketsToCart(@NotNull UUID ticketCategoryId, int amount, @NotNull List<Integer> ticketNumbers) throws NoPermissionException {
        List<TicketDTO> tickets = ticketService.addTicketsToCart(ticketCategoryId, amount, ticketNumbers);
        mutableShoppingCart.addAll(tickets);
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            return tickets;
        } else {
            throw new NoPermissionException();
        }
    }

    @NotNull
    @Override
    public List<EventDTO> findEventGenre(@NotNull String genre) {
        return eventService.findEventGenre(genre);
    }

    @NotNull
    @Override
    public List<EventDTO> findEventName(@NotNull String eventName) {
        return eventService.findEventName(eventName);
    }

    @NotNull
    @Override
    public String getActiveMQBroker() {
        return MessageService.INSTANCE.getBROKER_URL();
    }

    @NotNull
    @Override
    public List<Integer> getAvailableTicketNumbers(@NotNull UUID ticketCategoryId) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            return ticketCategoryService.getAvailableTicketNumbers(ticketCategoryId);
        } else {
            throw new NoPermissionException();
        }
    }

    @NotNull
    @Override
    public EventDTO getEventByCategoryId(@NotNull UUID ticketCategoryId) {
        return eventService.getEventByCategoryId(ticketCategoryId);
    }

    @Override
    public void checkoutFromCart(@NotNull List<UUID> list, int i) {
         orderService.checkoutFromCart(list, i);
    }

    @Override
    public void logout() {
        ticketService.removeTicketsFromCart(mutableShoppingCart.stream().map(TicketDTO::getId).toList());
        mutableShoppingCart.clear();
    }

    @Override
    public void markOrderAsCanceled(@NotNull UUID uuid) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            orderService.markOrderAsCanceled(uuid);
        } else {
            throw new NoPermissionException();
        }
    }

    @Override
    public void removeTicketsFromCart(@NotNull List<UUID> ticketIds) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN) || ldapUser.getPermissions().contains(Permission.SELLER)) {
            ticketService.removeTicketsFromCart(ticketIds);
            mutableShoppingCart.removeIf(ticket -> ticketIds.contains(ticket.getId()));
        } else {
            throw new NoPermissionException();
        }
    }

    @NotNull
    @Override
    public List<CustomerDTO> searchCustomerByName(@NotNull String name) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.ADMIN)|| ldapUser.getPermissions().contains(Permission.SELLER)) {
            try {
                return customerDBClient.searchCustomerByName(name);
            } catch (RemoteException e) {
                return new LinkedList<>();
            }
        } else {
            throw new NoPermissionException();
        }
    }


    @NotNull
    @Override
    public PaymentDTO getPaymentByOrderId(@NotNull UUID uuid) throws NoPermissionException {
        if (ldapUser.getPermissions().contains(Permission.SELLER) || ldapUser.getPermissions().contains(Permission.ADMIN)) {
            return paymentService.getPaymentByOrderId(uuid);
        } else {
            throw new NoPermissionException();
        }
    }
}
