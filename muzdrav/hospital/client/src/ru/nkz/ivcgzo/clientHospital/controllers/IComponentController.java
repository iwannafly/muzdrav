package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

public interface IComponentController {

    Component getComponent();

    String getPanelTooltipText();

    String getTitle();

    URL getIconURL();

}
