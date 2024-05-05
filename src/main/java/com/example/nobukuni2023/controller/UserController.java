package com.example.nobukuni2023.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nobukuni2023.entity.User;
import com.example.nobukuni2023.form.UserEditForm;
import com.example.nobukuni2023.repository.UserRepository;
import com.example.nobukuni2023.security.UserDetailsImpl;
import com.example.nobukuni2023.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserRepository userRepository;
	 private final UserService userService;
    
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }    
    
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        model.addAttribute("user", user);
        
        return "user/index";
    }
    
    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {        
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        UserEditForm userEditForm =  new UserEditForm(user.getId(), user.getName(), user.getEmail());
        
        model.addAttribute("userEditForm", userEditForm);
        
        return "user/edit";
    }

	@PostMapping("/update")
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);                       
        }
        
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        
        userService.update(userEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
        
        return "redirect:/user";
    }
	
	@GetMapping("/subsc")
    public String subsc(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) throws StripeException {        
		// Set your secret key. Remember to switch to your live secret key in production.
		// See your keys here: https://dashboard.stripe.com/apikeys
		Stripe.apiKey = "sk_test_51OLEXaJsSQOmZTMCrcb62esT4RG6bjjugAXnoxNd6E7gCTCJPdCGPRK1KgYHvgoro0A3ckUdXMCOoMeZmp6uwDeX00agPzG7vY";

		// The price ID passed from the client
		//   String priceId = request.queryParams("priceId");
		String priceId = "price_1PCcVZJsSQOmZTMC9yEmSw84";

		SessionCreateParams params = new SessionCreateParams.Builder()
		  //.setSuccessUrl("https://example.com/success.html?session_id={CHECKOUT_SESSION_ID}")
		  .setSuccessUrl("http://localhost:8080/?loggedIn")
		  .setCancelUrl("https://example.com/canceled.html")
		  .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
		  .addLineItem(new SessionCreateParams.LineItem.Builder()
		    // For metered billing, do not pass quantity
		    .setQuantity(1L)
		    .setPrice(priceId)
		    .build()
		  )
		  .build();

		Session session = Session.create(params);

		// Redirect to the URL returned on the Checkout Session.
		// With Spark, you can redirect with:
		//   response.redirect(session.getUrl(), 303);
		//   return "";
        
        
        return "user/subsc";
    }
	
}
