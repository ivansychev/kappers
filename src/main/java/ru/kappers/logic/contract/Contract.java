package ru.kappers.logic.contract;

import lombok.extern.log4j.Log4j;

import ru.kappers.model.KapperInfo;
import ru.kappers.model.User;
import java.util.Map;

/**
 * Created by Shoma on 29.09.2018.
 */
@Log4j
public class Contract implements IContract {
    @Override
    public KapperInfo initCapper(User user) {
        return null;
    }

    @Override
    public KapperInfo getKapperInfo(User user) {
        return null;
    }

    @Override
    public void blockTokens(User user, double amount) {

    }

    @Override
    public Map<User, Double> getBalance() {
        return null;
    }

    @Override
    public Map<User, KapperInfo> getAllInfo() {
        return null;
    }

    @Override
    public void unblockAmount(User user, double amount) {

    }
//    private Session session;
//
//    public Session getSession() {
//        return session;
//    }
//
//    public Contract() {
//        session = HibernateSessionFactory.getSession();
//    }
//
//    public Contract(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public KapperInfo initCapper(Users user) {
//        KapperInfo kapper;
//        if (user.getRoleId() != 3) {
//            log.error("У пользователя с иденитфикатором " + user.getUserName() + " нет роли Каппера. Его инициализация невозможна. ");
//            return null;
//        } else {
//            kapper = getKapperInfo(user);
//            if (kapper != null) {
//                log.error("Каппер "+user.getUserName()+" уже прошел инициализацию");
//                throw new IllegalArgumentException("Каппер "+user.getUserName()+" уже прошел инициализацию");
//            } else {
//                kapper = KapperInfo.builder().user(user).tokens(500d).bets(0).successBets(0).failBets(0).blockedTokens(0d).build();
//                session.beginTransaction();
//                session.saveOrUpdate(kapper);
//                session.getTransaction().commit();
//            }
//        }
//        return kapper;
//    }
//
//    @Override
//    public KapperInfo getKapperInfo(Users user) {
//        List list = session.createQuery("From KapperInfo where u_id = " + user.getUserId()).list();
//        if (list.size() > 0) {
//            return (KapperInfo) list.get(0);
//        }
//        return null;
//    }
//
//
//    @Override
//    public void blockTokens(Users user, double amount) {
//        KapperInfo from = getKapperInfo(user);
//        double fromBalance = from.getTokens();
//        if (fromBalance >= amount) {
//            session.beginTransaction();
//            from.setBlockedTokens(from.getBlockedTokens() + amount);
//            session.saveOrUpdate(from);
//            session.getTransaction().commit();
//        } else {
//            throw new IllegalArgumentException("Недостаточно средств на счете для блокировки");
//        }
//    }
//
//    @Override
//    public void unblockAmount(Users user, double amount) {
//        KapperInfo from = getKapperInfo(user);
//        double blockedTokens = from.getBlockedTokens();
//        if (blockedTokens >= amount) {
//            session.beginTransaction();
//            from.setBlockedTokens(blockedTokens - amount);
//            session.saveOrUpdate(from);
//            session.getTransaction().commit();
//        } else {
//            throw new IllegalArgumentException("Недостаточно средств на счете для блокировки");
//        }
//    }
//
//    @Override
//    public Map<Users, Double> getBalance() {
//        Map<Users, Double> map = new HashMap<>();
//        Map<Users, KapperInfo> all = getAllInfo();
//        for (Map.Entry<Users, KapperInfo> e : all.entrySet()) {
//            map.put(e.getKey(), e.getValue().getTokens());
//        }
//        return map;
//    }
//
//    @Override
//    public Map<Users, KapperInfo> getAllInfo() {
//        Map<Users, KapperInfo> map = new HashMap<>();
//        String hql = "FROM Users where role_id = '3'";
//        Query query = session.createQuery(hql);
//        List results = query.list();
//        KapperInfo kapper;
//        for (Object o : results) {
//            hql = "FROM KapperInfo where u_id = " + ((Users) o).getUserId();
//            query = session.createQuery(hql);
//            List kaps = query.list();
//            if (kaps.size() > 0) {
//                kapper = (KapperInfo) kaps.get(0);
//                map.put((Users) o, kapper);
//            }
//        }
//        return map;
//    }
//
//    public void deleteKapper(Users user) {
//        List list = session.createQuery("From KapperInfo where u_id = " + user.getUserId()).list();
//        if (list.size() > 0) {
//            KapperInfo kapper = (KapperInfo) list.get(0);
//            session.beginTransaction();
//            session.delete(kapper);
//            session.getTransaction().commit();
//        }
//    }

}
