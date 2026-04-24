package kr.higu.peelie.onboarding.interfaces.admin;

import jakarta.validation.Valid;
import kr.higu.peelie.onboarding.application.OnboardingAdminFacade;
import kr.higu.peelie.onboarding.domain.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/onboarding")
public class OnboardingAdminController {

    private final OnboardingAdminFacade onboardingAdminFacade;
    private final OnboardingAdminMapper onboardingAdminMapper;

    @GetMapping
    public String page(Model model, @RequestParam(required = false) String message) {
        populatePageModel(model, message);
        return "admin/onboarding";
    }

    @PostMapping("/questions")
    public String registerQuestion(
            @ModelAttribute @Valid OnboardingAdminForm.QuestionRequest form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            populatePageModel(model, "validation-error");
            return "admin/onboarding";
        }

        onboardingAdminFacade.registerQuestion(onboardingAdminMapper.toRegisterCommand(form));
        redirectAttributes.addAttribute("message", "question-created");
        return "redirect:/admin/onboarding";
    }

    @PostMapping("/questions/{questionId}")
    public String updateQuestion(
            @PathVariable Long questionId,
            @ModelAttribute @Valid OnboardingAdminForm.QuestionRequest form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            populatePageModel(model, "validation-error");
            return "admin/onboarding";
        }

        onboardingAdminFacade.updateQuestion(onboardingAdminMapper.toUpdateCommand(questionId, form));
        redirectAttributes.addAttribute("message", "question-updated");
        return "redirect:/admin/onboarding";
    }

    @PostMapping("/questions/{questionId}/delete")
    public String deleteQuestion(
            @PathVariable Long questionId,
            RedirectAttributes redirectAttributes
    ) {
        onboardingAdminFacade.deleteQuestion(questionId);
        redirectAttributes.addAttribute("message", "question-deleted");
        return "redirect:/admin/onboarding";
    }

    private void populatePageModel(Model model, String message) {
        model.addAttribute("questions", onboardingAdminMapper.toQuestionItems(onboardingAdminFacade.getQuestions()));
        model.addAttribute("questionForm", new OnboardingAdminForm.QuestionRequest());
        model.addAttribute("questionTypes", Question.QuestionType.values());
        model.addAttribute("message", message);
    }
}
