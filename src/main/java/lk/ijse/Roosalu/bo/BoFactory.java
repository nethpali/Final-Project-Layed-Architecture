package lk.ijse.Roosalu.bo;

import lk.ijse.Roosalu.bo.Custom.Impl.*;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){
    }
    public static BoFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BoFactory() : boFactory;
    }

    public enum BOTypes{
        AGENT,ASSESTS,EMPLOYEE,USER,PRODUCT,RAWMATERIAL
    }

    //Object creation logic for BO objects
    public SuperBo  getBO(BOTypes types){
        switch (types){
            case AGENT:
                return new AgentBoImpl();
            case ASSESTS:
                return new AssestsBoImpl();
            case EMPLOYEE:
                return  new EmployeeBoImpl();
            case USER:
                return new UserBoImpl();
            case PRODUCT:
                return new ProducrBOImpl();
            case RAWMATERIAL:
                return new RawMaterialBOImpl();
            default:
                return null;
        }
    }
}
