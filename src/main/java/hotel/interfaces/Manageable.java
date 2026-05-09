/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.interfaces;

import java.util.List;

public interface Manageable <T>{

    void add(T item);

    void update(T item);

    void delete(T item);

    List<T> viewAll();
}
