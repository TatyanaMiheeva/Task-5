package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CarDaoImp implements CarDao{
    private final SessionFactory sessionFactory;

    public CarDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<User> getUsers(String model, int series) {
        TypedQuery<User> queryUser = sessionFactory.getCurrentSession().createQuery(
                "from User user where user.car.model =:model and user.car.series=:series");
        queryUser.setParameter("model", model).setParameter("series", series);
        return queryUser.getResultStream().findAny();
    }
}
