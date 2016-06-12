package at.technikumwien.mwi.casestudy;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

	private Facebook facebook;
	private LinkedIn linkedIn;
	private Twitter twitter;
	private ConnectionRepository connectionRepository;

	@Inject
	public ProfileController(Facebook facebook, LinkedIn linkedIn, Twitter twitter,
			ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.linkedIn = linkedIn;
		this.twitter = twitter;
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(value = "facebook", method = RequestMethod.GET)
	public String helloFacebook(Model model) {
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		model.addAttribute("profile", facebook.userOperations().getUserProfile());
		return "facebook/profile";
	}

	@RequestMapping(value = "linkedin", method = RequestMethod.GET)
	public String helloLinkedIn(Model model) {
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
			return "redirect:/connect/linkedin";
		}
		model.addAttribute("profile", linkedIn.profileOperations().getUserProfileFull());
		return "linkedin/profile";
	}

	@RequestMapping(value = "twitter", method = RequestMethod.GET)
	public String helloTwitter(Model model) {
		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			return "redirect:/connect/twitter";
		}
		model.addAttribute("profile", twitter.userOperations().getUserProfile());
		return "twitter/profile";
	}

	@RequestMapping(value = "all", method = RequestMethod.GET)
	public String helloAll(Model model) {
		if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
			return "redirect:/connect/twitter";
		}
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
			return "redirect:/connect/linkedin";
		}
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}

		model.addAttribute("liprofile", linkedIn.profileOperations().getUserProfileFull());
		collcetTwDAta(model);
		collectFbData(model);
		return "allProfiles";
	}

	private void collcetTwDAta(Model model) {
		model.addAttribute("twprofile", twitter.userOperations().getUserProfile());
		model.addAttribute("timeline", twitter.timelineOperations().getHomeTimeline());
	}

	private void collectFbData(Model model) {
		model.addAttribute("fbprofile", facebook.userOperations().getUserProfile());
		PagedList<Post> feed = facebook.feedOperations().getFeed();

		System.out.println("----> " + feed.size());

		model.addAttribute("fbfeed", feed);
	}

}