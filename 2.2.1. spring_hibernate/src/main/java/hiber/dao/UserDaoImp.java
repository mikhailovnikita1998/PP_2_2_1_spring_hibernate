package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u");
      return query.getResultList();
   }

   @Override
   public User findUserByCarModelAndSeries(String carModel, int carSeries) {
      try {
         // Выполнение запроса к базе данных с использованием критериев Hibernate
         return sessionFactory.getCurrentSession()
                 .createQuery("SELECT u FROM User u WHERE u.car.model = :model AND u.car.series = :series", User.class)
                 .setParameter("model", carModel)
                 .setParameter("series", carSeries)
                 .getSingleResult();
      } catch (NoResultException e) {
         // Если ни один пользователь не найден, возвращаем null
         return null;
      }
   }

}
