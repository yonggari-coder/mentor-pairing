package toy_project.mentor_pairing.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import toy_project.mentor_pairing.exception.MentoringException;

@Slf4j
@ControllerAdvice
public class ViewExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MentoringException.class)
    public String handleMentoringException(MentoringException exception, Model model){
        model.addAttribute("mesesage", exception.getMessage());
        model.addAttribute("status", 400);
        return "error/businessError";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(Exception exception, Model model){
        model.addAttribute("message", "요청 처리 중 오류가 발생했습니다.");
        model.addAttribute("status", 500);
        return "error/buisnessError";
    }
}