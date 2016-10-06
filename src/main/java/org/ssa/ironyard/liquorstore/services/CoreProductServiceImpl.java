package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct;

@Service
public class CoreProductServiceImpl implements CoreProductService
{
    
    DAOCoreProduct daoCP;
    
    @Autowired
    public CoreProductServiceImpl(DAOCoreProduct daoCP)
    {
        this.daoCP = daoCP;
    }

    @Override
    public CoreProduct readCoreProduct(Integer id)
    {
        return daoCP.read(id);
    }

    @Override
    public List<CoreProduct> readAllCoreProduct()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreProduct editCoreProduct(CoreProduct coreproduct)
    {
        if(daoCP.read(coreproduct.getId()) == null)
            return null;
        
        CoreProduct cp = new CoreProduct(coreproduct.getId(),coreproduct.getName(),coreproduct.getTags(),coreproduct.getType(),coreproduct.getSubType(),coreproduct.getDescription());
        return daoCP.update(cp);
    }

    @Override
    public CoreProduct addCoreProduct(CoreProduct coreproduct)
    {
        CoreProduct cp = new CoreProduct(coreproduct.getId(),coreproduct.getName(),coreproduct.getTags(),coreproduct.getType(),coreproduct.getSubType(),coreproduct.getDescription());
        return daoCP.update(cp);
    }

    @Override
    public boolean deleteCoreProduct(Integer id)
    {
        if(daoCP.read(id) == null)
            return false;
        return daoCP.delete(id);
    }

}
