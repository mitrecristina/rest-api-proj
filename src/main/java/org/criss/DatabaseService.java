package org.criss;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.criss.dao.AutoturismUserLinkDao;
import org.criss.dao.AutoturismeDao;
import org.criss.dao.UserDao;
import org.criss.entities.*;
import org.criss.exceptions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DatabaseService {

    @Inject
    EntityManager em;

 /*  @Transactional
    public void createUser(UserDao userDao) {
        User user = new User();
        user.setFirstName(userDao.firstName);
        user.setLastName(userDao.lastName);
        user.setUsername(userDao.username);
        user.setPassword(userDao.password);


        em.persist(user);
        em.flush();
    }*/

    @SuppressWarnings("unchecked")
    public List<UserDao> getUsers() {

        Query q = em.createQuery("SELECT u FROM User u");
        List<User> userslist = q.getResultList();

        List<UserDao> userDaoList = new ArrayList<>();
        for (User u : userslist) {
            UserDao userDao = new UserDao();
            userDao.firstName = u.getFirstName();
            userDao.lastName = u.getLastName();
            userDao.username = u.getUsername();
            userDao.password = u.getPassword();
            userDao.money = u.getMoney();
            userDaoList.add(userDao);
        }


        return userDaoList;

    }

    public class TokenPairDate {
        public String token;

        public Date expirationDate;
    }


    public TokenPairDate token() {


        Instant expirationDate = Instant.now().plus(5, ChronoUnit.DAYS);

        TokenPairDate token = new TokenPairDate();

        token.token = tokenString();
        token.expirationDate = Date.from(expirationDate);

        return token;

    }

    public String tokenString() {

        UUID uuid = UUID.randomUUID();

        String uuidAsString = uuid.toString().replaceAll("-", "").substring(0, 20);

        System.out.println("Your token is: " + uuidAsString);

        return uuidAsString;

    }

    @Transactional
    public Long createUserwithToken(UserDao userDao) {
        Query q = em.createQuery("select count(u)  from User u where u.username = :username ");
        q.setParameter("username", userDao.username);
        Long result = (Long) q.getSingleResult();

        if (result == 0) {


            User user = new User();

            user.setFirstName(userDao.firstName);
            user.setLastName(userDao.lastName);
            user.setUsername(userDao.username);
            user.setPassword(userDao.password);
            user.setMoney(userDao.money);

            em.persist(user);

            TokenPairDate token = token();

            Token t = new Token();

            t.setToken(token.token);

            t.setExpirationDate(token.expirationDate);

            em.persist(t);

            user.setToken(t);

            em.persist(user);

            em.flush();


            return user.getId();
        } else {
            return null;
        }

    }

    @Transactional
    public String login(String username, String password) {
        Query q = em.createQuery("select u from User u where u.username = :username and u.password= :password");
        q.setParameter("username", username);
        q.setParameter("password", password);

        try {
            User result = (User) q.getSingleResult();

            Token a = result.getToken();

            Date date = a.getExpirationDate();

            Date date1 = new Date();


            if (date1.compareTo(date) > 0) {

                String token = tokenString();

                Instant expirationDate = Instant.now().plus(5, ChronoUnit.DAYS);
                Date expDate = Date.from(expirationDate);
                a.setExpirationDate(expDate);

                a.setToken(token);

                em.persist(a);

                em.flush();

                return result.getToken().getToken();

            } else {

                //  Token token = result.getToken();
                //  String t = token.getToken();
                return result.getToken().getToken();
            }


        } catch (NoResultException e) {
            return null;

        }


    }

    public List<AutoturismeDao> getAutoturisme() {

        Query q = em.createQuery("SELECT u FROM Autoturisme u");
        List<Autoturisme> autoturismelist = q.getResultList();

        List<AutoturismeDao> autoturismeDaoList = new ArrayList<>();
        for (Autoturisme a : autoturismelist) {
            AutoturismeDao autoturismeDao = new AutoturismeDao();
            autoturismeDao.name = a.getName();
            autoturismeDao.pieces = a.getPieces();
            autoturismeDao.price = a.getPrice();

            autoturismeDaoList.add(autoturismeDao);
        }


        return autoturismeDaoList;

    }

    @Transactional
    public String buy(String token, AutoturismeDao autdao)
            throws TokenExpirationException, UserNotFoundException, VehicleNotFoundException, VehicleOutOfStockException, NotEnoughMoneyException {

        User user = exceptionUserandToken(token);

        Query q = em.createQuery("select a From Autoturisme a where a.name = :name ");
        q.setParameter("name", autdao.name);

        Autoturisme autoturisme;
        try {
            autoturisme = (Autoturisme) q.getSingleResult();

            if (autdao.pieces > autoturisme.getPieces()) {
                throw new VehicleOutOfStockException();
            }

            if (autoturisme.getPrice() * autdao.pieces > user.getMoney()) {

                throw new NotEnoughMoneyException();

            }

        } catch (NoResultException e) {

            throw new VehicleNotFoundException();

        }
        for (int i = 0; i < autdao.pieces; i++) {

            Autoturism autoturism = new Autoturism();

            autoturism.setChassisSeries(chassisSeries());
            autoturism.setAutoturisme(autoturisme);

            em.persist(autoturism);

            AutoturismUserLink autoturismUserLink = new AutoturismUserLink();

            autoturismUserLink.setAutoturism(autoturism);
            autoturismUserLink.setUser(user);

            em.persist(autoturismUserLink);

        }

        autoturisme.setPieces(autoturisme.getPieces() - autdao.pieces);
        user.setMoney(user.getMoney() - (autdao.pieces * autoturisme.getPrice()));

        em.persist(user);

        em.persist(autoturisme);

        em.flush();


        return null;

    }

    public static String chassisSeries() {
        UUID uuid = UUID.randomUUID();

        String uuidAsString = uuid.toString().replaceAll("-", "").substring(0, 17);

        System.out.println("Your Chassis Series is: " + uuidAsString);

        return uuidAsString;

    }

    public List<AutoturismUserLinkDao> getAutoturismUser(String token) throws TokenExpirationException, UserNotFoundException {


        Query q = em.createQuery("select a.chassisSeries, am.name " +
                "from AutoturismUserLink aul " +
                "join User u on (u=aul.user) " +
                "join Autoturism a on (a=aul.autoturism) " +
                "join Autoturisme am on (am=a.autoturisme) " +
                " where u = :user ");

        User user = exceptionUserandToken(token);
        System.out.println(user.getId());
        q.setParameter("user", user);

        List<Object[]> resultList = q.getResultList();

        List<AutoturismUserLinkDao> autoturismUserDaoList = new ArrayList<>();

        for (Object[] obj : resultList) {

            AutoturismUserLinkDao autoturismUserDao = new AutoturismUserLinkDao();
            autoturismUserDao.chassisSeries = (String) obj[0];
            autoturismUserDao.name = (String) obj[1];


            autoturismUserDaoList.add(autoturismUserDao);
        }


        return autoturismUserDaoList;

    }

    public User exceptionUserandToken(String token) throws TokenExpirationException, UserNotFoundException {
        Query q = em.createQuery("select u " +
                "from User u " +
                "join Token t on (u.token =t) " +
                "where t.token = :token ");

        q.setParameter("token", token);

        User user;

        try {

            user = (User) q.getSingleResult();

            Date date = user.getToken().getExpirationDate();

            Date date1 = new Date();

            if (date1.compareTo(date) > 0) {

                throw new TokenExpirationException();

            }

        } catch (NoResultException e) {

            throw new UserNotFoundException();
        }

        return user;
    }

    @Transactional
    public String refreshToken(String token) throws UserNotFoundException {
        Query q = em.createQuery("select u " +
                "from User u " +
                "join Token t on (u.token =t) " +
                "where t.token = :token ");

        q.setParameter("token", token);

        User user;

        try {

            user = (User) q.getSingleResult();

            TokenPairDate token1 = token();

            Token t = user.getToken();

            t.setToken(token1.token);

            t.setExpirationDate(token1.expirationDate);

            em.persist(t);

            em.flush();

            return t.getToken();


        } catch (NoResultException e) {

            throw new UserNotFoundException();
        }


    }
}









