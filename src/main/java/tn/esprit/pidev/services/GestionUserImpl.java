package tn.esprit.pidev.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GestionUserImpl implements IGestionUser{
    @Autowired
    IUserRepository ur;
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<User> findAll(){return ur.findAll();}
    @Override
    public User add(User user){return ur.save(user);}
    @Override
    public  User update(User user){
        User thisUser = ur.findUserByEmail(user.getEmail());
        return ur.save( thisUser);
    }
    @Override
    public User findById(Integer id){return ur.findById(id).orElse(null);}
    @Override
    public void delete(Integer id) {
        User user = findById(id);
        user.setIsArchived(true);
        ur.save(user);
    }

    @Override
    public User findUserBymail(String mail) {
        return ur.findUserByEmail(mail);
    }

    @Override
    public List<User> findAllActive() {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.isArchived = false", User.class).getResultList();
    }
    @Override
    public Optional<User> findByEmail(String user){return ur.findByEmail(user);}


}
