package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      // Создаем пользователей с машинами
      User user1 = new User("John", "Doe", "john.doe@example.com");
      Car car1 = new Car("Toyota", 123456);
      user1.setCar(car1);

      User user2 = new User("Alice", "Smith", "alice.smith@example.com");
      Car car2 = new Car("Toyota", 123456);
      user2.setCar(car2);

      // Добавляем пользователей в базу данных
      userService.add(user1);
      userService.add(user2);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      String carModel = "Toyota";
      int carSeries = 123456;
      User foundUser = userService.findUserByCarModelAndSeries(carModel, carSeries);

      if (foundUser != null) {
         System.out.println("Пользователь, владеющий автомобилем модели " + carModel + " и серии " + carSeries + ":");
         System.out.println("Id = " + foundUser.getId());
         System.out.println("First Name = " + foundUser.getFirstName());
         System.out.println("Last Name = " + foundUser.getLastName());
         System.out.println("Email = " + foundUser.getEmail());
      } else {
         System.out.println("Пользователь, владеющий автомобилем модели " + carModel + " и серии " + carSeries + ", не найден.");
      }

      context.close();
   }
}
