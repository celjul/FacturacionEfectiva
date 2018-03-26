package com.entich.ezfact.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImp {

    @Value("${facturacion.path.logos}")
    private String pathLogos;

    @Value("${facturacion.path.plantillas}")
    private String pathPlantillas;

    @Value("${facturacion.path.plantillas.p0}")
    private String pathPlantilla0;

    @Value("${facturacion.path.plantillas.p1}")
    private String pathPlantilla1;

    @Value("${facturacion.path.plantillas.p2}")
    private String pathPlantilla2;

    @Value("${facturacion.path.plantillas.p3}")
    private String pathPlantilla3;

    @Value("${facturacion.path.plantillas.default}")
    private String pathPlantillaDefault;

    @Value("${facturacion.recaptcha.clavesecreta}")
    private String claveSecreta;

    @Value("${facturacion.path.plantillas.personalizada}")
    private String pathPlantillaPersonalizada;

    @Value("${sendingblue.host}")
    private String sendingblueHost;

    @Value("${sendingblue.key}")
    private String sendingblueKey;

    @Value("${email.direccion.origen.email}")
    private String emailDirOrigenEmail;

    @Value("${email.direccion.origen.nombre}")
    private String emailDirOrigenNombre;

    public String getPathLogos() {
        return pathLogos;
    }

    public String getPathPlantillas() {

        return pathPlantillas;
    }

    public String getPathPlantilla0() {

        return pathPlantilla0;
    }

    public String getPathPlantilla1() {

        return pathPlantilla1;
    }

    public String getPathPlantilla2() {

        return pathPlantilla2;
    }

    public String getPathPlantilla3() {

        return pathPlantilla3;
    }
    public String getPathPlantillaPersonalizada() {
        return pathPlantillaPersonalizada;
    }

    public String getClaveSecreta() {
        return claveSecreta;
    }

    public String getPathPlantillaDefault() {
        return pathPlantillaDefault;
    }

    public String getSendingblueHost() {
        return sendingblueHost;
    }

    public void setSendingblueHost(String sendingblueHost) {
        this.sendingblueHost = sendingblueHost;
    }

    public String getSendingblueKey() {
        return sendingblueKey;
    }

    public void setSendingblueKey(String sendingblueKey) {
        this.sendingblueKey = sendingblueKey;
    }

    public String getEmailDirOrigenEmail() {
        return emailDirOrigenEmail;
    }

    public void setEmailDirOrigenEmail(String emailDirOrigenEmail) {
        this.emailDirOrigenEmail = emailDirOrigenEmail;
    }

    public String getEmailDirOrigenNombre() {
        return emailDirOrigenNombre;
    }

    public void setEmailDirOrigenNombre(String emailDirOrigenNombre) {
        this.emailDirOrigenNombre = emailDirOrigenNombre;
    }
}
