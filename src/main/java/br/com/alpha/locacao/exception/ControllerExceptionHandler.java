package br.com.alpha.locacao.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			erros.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(), "Existe campos invalidos", LocalDateTime.now(),
				erros);
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);

	}
	

	@ExceptionHandler(ConstantsException.class)
	private ResponseEntity<Object> handleConstantsException(ConstantsException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	            HttpStatus.BAD_REQUEST.value(),
	            "Constants Invalidos",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DadosBancariosException.class)
	private ResponseEntity<Object> handleDadosBancariosException(DadosBancariosException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	            HttpStatus.UNPROCESSABLE_ENTITY.value(),
	            "Erro no envio de dados bancarios",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(EmailException.class)
	private ResponseEntity<Object> handleEmailException(EmailException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	    		HttpStatus.CONFLICT.value(),
	            "Erro no envio de e-mail",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(SenhaException.class)
	private ResponseEntity<Object> handleSenhaException(SenhaException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	            HttpStatus.UNPROCESSABLE_ENTITY.value(),
	            "Erro na senha fornecida",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(TelefoneException.class)
	private ResponseEntity<Object> handleTelefoneException(TelefoneException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	            HttpStatus.UNPROCESSABLE_ENTITY.value(),
	            "Erro no telefone fornecido",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	@ExceptionHandler(CpfException.class)
	private ResponseEntity<Object> handleCpfException(CpfException ex, WebRequest request) {
	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMessage());

	    ErroResposta erroResposta = new ErroResposta(
	    		HttpStatus.CONFLICT.value(),
	            "Erro no CPF fornecido",
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erroResposta, HttpStatus.CONFLICT);
	}
	
	
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> handleRuntimeException(RuntimeException ex, WebRequest request) {
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());

        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor.",
                LocalDateTime.now(),
                erros
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	 @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ErroResposta> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
	        List<String> erros = new ArrayList<>();
	        erros.add(ex.getMessage());

	        ErroResposta erroResposta = new ErroResposta(
	                HttpStatus.BAD_REQUEST.value(),
	                "Requisição inválida",
	                LocalDateTime.now(),
	                erros
	        );

	        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @ExceptionHandler(EntityNotFoundException.class)
	    public ResponseEntity<ErroResposta> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
	        List<String> erros = new ArrayList<>();
	        erros.add(ex.getMessage());

	        ErroResposta erroResposta = new ErroResposta(
	                HttpStatus.NOT_FOUND.value(),
	                "Recurso não encontrado",
	                LocalDateTime.now(),
	                erros
	        );

	        return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
	    }

}
