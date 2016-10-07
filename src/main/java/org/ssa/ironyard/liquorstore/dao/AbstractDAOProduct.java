package org.ssa.ironyard.liquorstore.dao;

import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;

public abstract class AbstractDAOProduct extends AbstractSpringDAO<Product> implements DAOProduct
{

    protected AbstractDAOProduct(ORM<Product> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }
    
    public abstract List<Product> searchProducts(List<Tag> tags, List<Type> types);


}
