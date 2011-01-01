package fr.unice.miage.vroomaniacs.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import fr.unice.miage.vroomaniacs.circuit.Circuit;

public class Memento 
{
	/** Object to save, must implements Serializable */
	private Serializable objToSave;
	/** Memento Constructor */
	public Memento(Serializable obj,File p_file)
	{
		objToSave = obj;
		this.objSaving(p_file);
		CareTaker.getInstance().addMemento(this);
	}
	/** objToSave getter 
	 * @return l'objet a sauvegarder*/
	public Serializable getObjToSave()
	{
		return objToSave;
	}
	/** Saving method
	 * @param p_file fichier ou sauvegarder l'objet*/
	private void objSaving(File p_file)
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream(p_file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objToSave);
			oos.flush();
			oos.close();
			fos.flush();
			fos.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/** Loading method
	 * @param p_file le fichier ou est situe l'objet a charger
	 * @return l'objet charge*/
	public static Serializable objLoading(File p_file)
	{
		Circuit circuit = Circuit.getInstance();
		try {
			FileInputStream fis;
			fis = new FileInputStream(p_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			circuit = (Circuit) obj;
			//System.out.println("load :"+circuit.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally
		{
			return circuit;
		}
	}
}
