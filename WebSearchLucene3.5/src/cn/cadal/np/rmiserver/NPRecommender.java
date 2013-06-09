package cn.cadal.np.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NPRecommender extends Remote
{
	public List getNPRecommendResults(String bookURL)throws RemoteException;
}
