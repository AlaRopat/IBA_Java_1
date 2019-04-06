package by.iba.web.dao;

import java.io.Serializable;

public interface Identified<PK extends Serializable> {
    PK getId();
}