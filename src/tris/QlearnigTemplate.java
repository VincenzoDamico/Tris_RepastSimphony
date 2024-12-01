package tris;

import repast.simphony.engine.environment.RunEnvironment;

public abstract class QlearnigTemplate {
	
	public void step () {
		while(true) {
			//possibleAction.isEmpty() in teoria non dovrebbe essere possibile ma il controllo lo metto lo stesso 
			if(epsilonPolicy()) { 
				explore();
			}else {
				updateQtable();
				break;
			}
		}
		if(isDone()) {
			RunEnvironment.getInstance().endRun();
		}
	}

	protected abstract boolean isDone();

	protected abstract boolean epsilonPolicy();

	protected abstract void explore();

	protected abstract void updateQtable();
}
