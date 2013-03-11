/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.helmut.test.jpacache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Simple {

    @Id
    @GeneratedValue
    private long id;
}
