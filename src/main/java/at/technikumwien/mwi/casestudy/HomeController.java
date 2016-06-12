package at.technikumwien.mwi.casestudy;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

	private ConnectionRepository connectionRepository;

	@Inject
	public HomeController(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {

		boolean allLoggedIn = true;

		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			model.addAttribute("fb", true);
		} else {
			model.addAttribute("fb", false);
			allLoggedIn = false;
		}
		if (connectionRepository.findPrimaryConnection(Twitter.class) != null) {
			model.addAttribute("tw", true);
		} else {
			model.addAttribute("tw", false);
			allLoggedIn = false;
		}
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) != null) {
			model.addAttribute("li", true);
		} else {
			model.addAttribute("li", false);
			allLoggedIn = false;
		}

		if (allLoggedIn)
			return "redirect:/profiles/all";

		return "home";
	}
}