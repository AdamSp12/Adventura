/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author xzenj02
 */
public interface Subject {
    
    void registerObserver(Observer observer);
    
    void deleteObserver (Observer observer);
    
    void notifyAllObservers();
    
}
