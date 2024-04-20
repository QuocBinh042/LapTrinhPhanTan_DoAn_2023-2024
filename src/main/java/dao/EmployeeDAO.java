package dao;

import java.rmi.RemoteException;
import java.util.List;

import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.EmployeeService;

public class EmployeeDAO implements EmployeeService {
	private EntityManager em;

	public EmployeeDAO() {
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}

	@Override
	public List<Employee> getAllEmployees() throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select employee FROM Employee employee", Employee.class).getResultList();
	}

	@Override
	public boolean addEmployee(Employee employee) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if (em.find(Employee.class, employee.getId()) != null) {
				return false;
			}
			em.persist(em.merge(employee));
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
	public boolean updateEmployee(Employee employee) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(employee);
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
	public boolean updatePassword(String phoneNumber, String password) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Employee e = em.createQuery("SELECT e FROM Employee e WHERE e.phoneNumber = :phoneNumber", Employee.class)
					.setParameter("phoneNumber", phoneNumber).getSingleResult();
			if (e == null) {
				return false;
			}
			e.setPassword(password);
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
	public boolean deleteEmployeeByID(int employeeID) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Employee e = em.find(Employee.class, employeeID);
			if (e == null) {
				return false;
			}
			em.remove(e);
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
	public Employee checkAccount(String phoneNumber, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery(
				"SELECT employee FROM Employee employee WHERE employee.phoneNumber = :phoneNumber AND employee.password = :password",
				Employee.class).setParameter("phoneNumber", phoneNumber).setParameter("password", password)
				.getSingleResult();
	}

	@Override
	public Employee getEmployeeByID(int employeeID) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return em.find(Employee.class, employeeID);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> getEmployeesByPosition(String position) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT employee FROM Employee employee WHERE employee.position = :position",
				Employee.class).setParameter("position", position).getResultList();
	}

	@Override
	public List<Employee> getEmployeesByStatus(Boolean EmployeeStatus) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT employee FROM Employee employee WHERE employee.employeeStatus = :EmployeeStatus",
				Employee.class).setParameter("EmployeeStatus", EmployeeStatus).getResultList();
	}

}
