package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Booking;

public interface BookingSevice extends Remote {
	public boolean createBooking(Booking booking) throws RemoteException;

	public List<Booking> getAllBooking() throws RemoteException;

	public List<Booking> getBookingsByStatus(int status) throws RemoteException;

	public Booking searchBookingByID(int bookingID) throws RemoteException;

	public boolean updateBookingStatusByID(int status, int bookingID) throws RemoteException;

}
