package tris;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
public abstract class QlearnigTemplate {

	public void QlearningAlg () {
		if(epsilonPolicy()) { 
			exploreAction();
		}else {
			greedyAction();
		}
		if(isDone()) {
			end();
		}	
	}

	protected abstract void end();

	protected abstract boolean isDone();

	protected abstract boolean epsilonPolicy();

	protected abstract void exploreAction();

	protected abstract void greedyAction();
}
