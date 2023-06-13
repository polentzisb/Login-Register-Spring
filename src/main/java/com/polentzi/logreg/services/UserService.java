package com.polentzi.logreg.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.polentzi.logreg.models.User;
import com.polentzi.logreg.repositories.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

// registrar el usuario y hacer Hash a su password
	public User registerUser(User user, BindingResult resultado) {
		User userRegistrado = userRepo.findByEmail(user.getEmail());
		if (userRegistrado != null) {
			resultado.rejectValue("email", "Matches", "Correo electronico ya existe");
		}
		if(!user.getPassword().equals(user.getPasswordConfirmation())) {
			resultado.rejectValue("password", "Matches", "La confirmacion de contraseña debe coincidir");
		}
		if(resultado.hasErrors()) {
        	return null;
        }

		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);		
		return userRepo.save(user);
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public User findUserById(Long id) {
		Optional<User> u = userRepo.findById(id);

		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

//autenticar usuario (LOGIN)

	public boolean authenticateUser(String email, String password, BindingResult resultado) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			resultado.rejectValue("email", "Matches", "Email no válido");
			return false;
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				resultado.rejectValue("password", "Matches", "Password no es válido");
				return false;
			}
		}
	}

}
