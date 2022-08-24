package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static uk.gov.dwp.uc.pairtest.TicketConstants.*;


public class TicketServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */

    private final SeatReservationService seatReservationService = new SeatReservationServiceImpl();

    private final TicketPaymentService ticketPaymentService = new TicketPaymentServiceImpl();

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        validateTickets(accountId, ticketTypeRequests);

        int numberOfSeatsToReserve = Arrays.stream(ticketTypeRequests).filter(t -> (t.getTicketType().equals(TicketTypeRequest.Type.ADULT)
                || t.getTicketType().equals(TicketTypeRequest.Type.CHILD))).map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        seatReservationService.reserveSeat(accountId, numberOfSeatsToReserve);

        int ticketPrice = getTicketsPrice(ticketTypeRequests);
        ticketPaymentService.makePayment(accountId, ticketPrice);
    }

    private int getTicketsPrice(TicketTypeRequest... ticketTypeRequests) {
        int adultsTicketToReserve = Arrays.stream(ticketTypeRequests).filter(t -> t.getTicketType().equals(TicketTypeRequest.Type.ADULT)).
                map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        int childTicketToReserve = Arrays.stream(ticketTypeRequests).filter(t -> t.getTicketType().equals(TicketTypeRequest.Type.CHILD)).
                map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        int infantTicketToReserve = Arrays.stream(ticketTypeRequests).filter(t -> t.getTicketType().equals(TicketTypeRequest.Type.INFANT)).
                map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        return (adultsTicketToReserve * ADULT_TICKET_PRICE) +
                (childTicketToReserve * CHILD_TICKET_PRICE) +
                (infantTicketToReserve * INFANT_TICKET_PRICE);
    }

    private void validateTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) {

        if(ticketTypeRequests == null) {
            throw new InvalidPurchaseException("Ticket Request cannot be null");
        }

        if(accountId == null) {
            throw new InvalidPurchaseException("AccountId cannot be null");
        }

        boolean nullPresence = Arrays.stream(ticketTypeRequests).anyMatch(s -> ((s == null) || (s.getTicketType() == null)));
        if(nullPresence) {
            throw new InvalidPurchaseException("Ticket Request cannot be null");
        }


        Optional<Integer> inValidTickets = Arrays.stream(ticketTypeRequests).map(TicketTypeRequest::getNoOfTickets).filter(t -> (t <= 0)).findAny();
        if (inValidTickets.isPresent()) {
            throw new InvalidPurchaseException("Ticket request has invalid number of tickets. It must be greater than 0");
        }

        int numberOfTicketsPurchased = Arrays.stream(ticketTypeRequests).map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        if (numberOfTicketsPurchased > MAX_TICKETS) {
            throw new InvalidPurchaseException(String.format("Number of tickets purchased cannot be more thn %d", MAX_TICKETS));
        }

        int adultsTicketsToReserve = Arrays.stream(ticketTypeRequests).filter(t -> t.getTicketType().equals(TicketTypeRequest.Type.ADULT)).
                map(TicketTypeRequest::getNoOfTickets).reduce(0, Integer::sum);
        if (adultsTicketsToReserve <= 0) {
            throw new InvalidPurchaseException("Atleast one adult must be present in the tickets list purchased");
        }
    }

}
