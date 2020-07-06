package dev.hrijal.pacman.entities;

public interface Subject
{
	public void registerGhostCollisionObserver(GhostCollisionObserver o);
	public void registerStaticCollisionObserver(StaticCollisionObserver o);
	public void removeGhostCollisionObserver(GhostCollisionObserver o);
	public void removeStaticCollisionObserver(StaticCollisionObserver o);
	public void notifyGhostCollisionObservers();
	public void notifyStaticCollisionObservers();
}
