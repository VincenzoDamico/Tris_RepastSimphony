package tris;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import utils.Pair;
public abstract class QlearnigTemplate {

	public void QlearningAlg () {
		Pair<String, Pair<Integer,Integer>>state_action=epsilonGreedyPolicy();
		
		updateQtable(state_action.getFirst(),state_action.getSecond());

		if(isDone()) {
			end();
		}	
	}
	
	 public Pair<String, Pair<Integer,Integer>> epsilonGreedyPolicy(){
		Pair<String, Pair<Integer,Integer>>ret;
		if(epsilonPolicy()) { 
			 ret=exploreAction();
		}else {
			ret=greedyAction();
		}
		return ret;
	 }

	protected abstract void end();

	protected abstract boolean isDone();

	protected abstract boolean epsilonPolicy();

	protected abstract Pair<String, Pair<Integer,Integer>> exploreAction();

	protected abstract Pair<String, Pair<Integer,Integer>> greedyAction();
	
	protected abstract void updateQtable(String state, Pair<Integer,Integer> action);

}
