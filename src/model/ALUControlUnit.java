package model;

import exception.ALUControlUnitException;

public class ALUControlUnit {
	
	BitData output;
	BitData funct;
	
	private boolean[] controlSignal;
	public static final int SIGNAL_SIZE = 2;
	
	public ALUControlUnit() throws ALUControlUnitException {
		super();
		
		boolean[] initSignal = {false, false};
		
		this.funct = null;
		this.output = new BitData(3);
		this.controlSignal = initSignal;
	}
	
	public void setControlSignal(boolean signal1, boolean signal0) {
		this.controlSignal[0] = signal1;
		this.controlSignal[1] = signal0;
	}
	
	public void setFunct(boolean[] funct) throws ALUControlUnitException {
		if(funct.length == Instruction.FUNCT_SIZE) {			
			this.funct = BitData.booleanToBitData(funct);
		}
		else {
			throw new ALUControlUnitException("Funct need to have " + Instruction.FUNCT_SIZE + " bits. Bits: " + funct.length);
		}
	}
	
	public BitData getFunct() {
		return this.funct;
	}
	
	public boolean[] getControlSignal() {
		return this.controlSignal;
	}
	
	public BitData getOutput() throws ALUControlUnitException {
		if(this.funct != null) {
			BitData functAux = this.funct.getInverted();
			BitData signalAux = BitData.booleanToBitData(this.controlSignal).getInverted();
			
			this.output.set(2, (functAux.get(3) || functAux.get(0)) && signalAux.get(1));
			this.output.set(1, !signalAux.get(1) || !functAux.get(2));
			this.output.set(0, (functAux.get(1) && signalAux.get(1)) || signalAux.get(0));
			
			return this.output;
		}
		else {
			throw new ALUControlUnitException("Funct not setted! Funct: null");
		}
	}
	
	public boolean getJR() throws ALUControlUnitException {
		if(this.funct != null) {
			BitData functAux = this.funct.getInverted();
			
			return (!functAux.get(0) && !functAux.get(1) && !functAux.get(2) && functAux.get(3));
		}
		else {
			throw new ALUControlUnitException("Funct not setted! Funct: null");
		}
	}
	
	
	
	
}
