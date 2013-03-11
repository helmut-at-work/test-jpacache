package de.helmut.test.jpacache;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ParentNode {

    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentNode")
    private Collection<ChildNode> childs = new ArrayList<ChildNode>();

    public Collection<ChildNode> getChilds() {
        return childs;
    }

    public void setChilds(Collection<ChildNode> childs) {
        this.childs = childs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParentNode other = (ParentNode) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
