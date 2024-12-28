package tris;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
public abstract class QlearnigTemplate {

	public void QlearningAlg () {
		while(true) {
			if(epsilonPolicy()) { 
				explore();
			}else {
				updateQtable();
				if(isDone()) {
					end();
				}
				break;
			}
		}
	}

	protected abstract void end();

	protected abstract boolean isDone();

	protected abstract boolean epsilonPolicy();

	protected abstract void explore();

	protected abstract void updateQtable();
}
