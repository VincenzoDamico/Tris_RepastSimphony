package tris;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
public abstract class QlearnigTemplate {

//int epsilon,int alfa , int reward , int discount_factor da metter e o no ?
	public void QlearningAlg () {
		while(true) {
			if(epsilonPolicy()) { 
				explore();
			}else {
				updateQtable();
				break;
			}
			if(isDone()) {
				end();
			}
		}
	}

	protected abstract void end();

	protected abstract boolean isDone();

	protected abstract boolean epsilonPolicy();

	protected abstract void explore();

	protected abstract void updateQtable();
}
