package uk.gov.dwp.uc.pairtest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static uk.gov.dwp.uc.pairtest.TicketConstants.*;

public class TicketServiceImplTest {

    @Mock
    private SeatReservationService seatReservationServiceMock;

    @Mock
    private TicketPaymentService ticketPaymentServiceMock;

    private TicketService ticketService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketService = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketService, "seatReservationService", seatReservationServiceMock);
        ReflectionTestUtils.setField(ticketService, "ticketPaymentService", ticketPaymentServiceMock);
        doNothing().when(seatReservationServiceMock).reserveSeat(anyLong(), anyInt());
        doNothing().when(ticketPaymentServiceMock).makePayment(anyLong(), anyInt());
    }

    @Test
    public void test_ForNulls() {
        assertNotNull(seatReservationServiceMock);
        assertNotNull(ticketPaymentServiceMock);
        assertNotNull(ticketService);
    }

    @Test
    public void test_Verify_CorrectPriceAndSeats_InBooking() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 5);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        int expectedPriceOfTickets = (5 * ADULT_TICKET_PRICE) + (4 * CHILD_TICKET_PRICE) +
                (3 * INFANT_TICKET_PRICE);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock).reserveSeat(accountId, 9);
        verify(ticketPaymentServiceMock).makePayment(accountId, expectedPriceOfTickets);
    }

    @Test
    public void test_Verify_CorrectPriceAndSeats_MaxNoOfTicketsBooking() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 8);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 7);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 5);
        int expectedPriceOfTickets = (8 * ADULT_TICKET_PRICE) + (7 * CHILD_TICKET_PRICE) +
                (5 * INFANT_TICKET_PRICE);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock).reserveSeat(accountId, 15);
        verify(ticketPaymentServiceMock).makePayment(accountId, expectedPriceOfTickets);
    }

    @Test
    public void test_Verify_CorrectPriceAndSeats_OnlyAdultTicketsBooking() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 9);
        int expectedPriceOfTickets = 9 * ADULT_TICKET_PRICE ;
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one );
        verify(seatReservationServiceMock).reserveSeat(accountId, 9);
        verify(ticketPaymentServiceMock).makePayment(accountId, expectedPriceOfTickets);
    }

    @Test
    public void test_Verify_CorrectTicketData_TwentyTickets() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 13);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        Long accountId = 1L;
        int expectedPriceOfTickets = (13 * ADULT_TICKET_PRICE) + (4 * CHILD_TICKET_PRICE) +
                (3 * INFANT_TICKET_PRICE);
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock).reserveSeat(accountId, 17);
        verify(ticketPaymentServiceMock).makePayment(accountId, expectedPriceOfTickets);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_NegativeTickets() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -5);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        int expectedPriceOfTickets = (5 * ADULT_TICKET_PRICE) + (4 * CHILD_TICKET_PRICE) +
                (3 * INFANT_TICKET_PRICE);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }

    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_ZeroTickets() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }


    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_GreaterTwentyTickets() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 14);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest three = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two, three);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }


    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_ChildAndInfantTickets() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        TicketTypeRequest two = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one, two);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }

    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_OnlyChildrenInBooking() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 4);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }

    @Test(expected = InvalidPurchaseException.class)
    public void test_Verify_InCorrectTicketData_OnlyInfantsInBooking() {
        TicketTypeRequest one = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3);
        Long accountId = 1L;
        ticketService.purchaseTickets(accountId, one);
        verify(seatReservationServiceMock, never()).reserveSeat(accountId, anyInt());
        verify(ticketPaymentServiceMock, never()).makePayment(accountId, anyInt());
    }
}
