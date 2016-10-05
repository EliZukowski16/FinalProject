package org.ssa.ironyard.liquorstore.dao;

import org.ssa.ironyard.liquorstore.model.CoreProduct;

public interface DAOCoreProduct extends DAO<CoreProduct>
{

    CoreProduct insertTag(CoreProduct domain);

}
