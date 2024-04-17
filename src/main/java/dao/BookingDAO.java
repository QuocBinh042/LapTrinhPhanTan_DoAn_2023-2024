package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

import entity.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.BookingSevice;

public class BookingDAO extends UnicastRemoteObject implements BookingSevice{
	private EntityManager em;
	
	public BookingDAO() throws RemoteException{
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}

	@Override
	public boolean createBooking(Booking booking) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if(em.find(Booking.class, booking.getId()) != null)
				return false;
			
			em.merge(booking);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}	
		return false;
	}

	@Override
	public List<Booking> getAllBooking() throws RemoteException {
		// TODO Auto-generated method stub
		return (List<Booking>) em.createQuery("select b from Booking b").getResultList();
	}

	@Override
	public Booking searchBookingByID(int bookingID) throws RemoteException {
		// TODO Auto-generated method stub
		return (Booking) em.createQuery("select b from Booking b where b.id = :id")
				 .setParameter("id", bookingID).getSingleResult();
	}

	@Override
	public boolean updateBookingStatusByID(int status, int bookingID) throws RemoteException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if(em.find(Booking.class, bookingID) == null)
				return false;
			
			em.createQuery("update Booking set bookingStatus = :status where id = :bookingID")
			  .setParameter("status", status)
			  .setParameter("bookingID", bookingID)
			  .executeUpdate();
			tx.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}
	
	public static void main(String[] args) throws RemoteException{
		Booking booking = new Booking(0, LocalDate.now(), LocalDate.now(), 0, null, null, null, null);
		BookingDAO bookingDAO = new BookingDAO();
//		bookingDAO.createBooking(booking);
//		
//		bookingDAO.getAllBooking().forEach(b -> System.out.println(b));
//		Booking b = bookingDAO.searchBookingByID(3);
//		System.out.println(b);
		System.out.println(bookingDAO.updateBookingStatusByID(1, 3));
	}
	
}
