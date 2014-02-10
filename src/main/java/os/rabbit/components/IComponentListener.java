package os.rabbit.components;

public interface IComponentListener {

	@Deprecated
	void afterBuild();

	void initial();
	
	void beforeRender();

	void afterRender();
}
