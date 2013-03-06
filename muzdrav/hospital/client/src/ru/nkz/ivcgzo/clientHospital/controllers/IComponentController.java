package ru.nkz.ivcgzo.clientHospital.controllers;

import java.awt.Component;
import java.net.URL;

/**
 * Интерфейс для имплементации вспомогательными панелями
 * Позволяет добавлять информацию о них в TabPane на главной форме
 */
public interface IComponentController {

    Component getComponent();

    String getPanelTooltipText();

    String getTitle();

    URL getIconURL();

}
