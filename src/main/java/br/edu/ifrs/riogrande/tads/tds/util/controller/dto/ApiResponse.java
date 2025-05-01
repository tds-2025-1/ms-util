package br.edu.ifrs.riogrande.tads.tds.util.controller.dto;


public class ApiResponse<T> {
    
    private final T response;

    public ApiResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

}
