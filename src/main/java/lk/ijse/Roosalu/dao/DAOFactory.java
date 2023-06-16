package lk.ijse.Roosalu.dao;

import lk.ijse.Roosalu.dao.Custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        AGENT,ASSESTS,EMPLOYEE,USER,PRODUCT,RAWMATERIAL
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case AGENT:
                return new AgentDAOImpl();
            case ASSESTS:
                return new AssestsDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case USER:
                return new UserDAOImpl();
            case PRODUCT:
                return  new ProductDAOImpl();
            case RAWMATERIAL:
                return  new RawMaterialDAOImpl();
            default:
                return null;
        }
    }
}
