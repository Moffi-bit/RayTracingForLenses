package fits;

public class RayTracing {
	protected float[] slopes;
	protected float[] alpha;
	
	/**
	 * R = m z + c
	 * X = R cos(alpha)+ X0
	 * Y = R sin (alpha)+ Y0
	 * 
	 * M = Slope of the line
	 * Z = Independent variable measuring the separation between the planes (-1000, 0, 1000)
	 * C = -(M * Zplane)
	 * R = How far is the projection of the ray from the starting position
	 * 
	 * @return
	 */
	public float[] rayTracingBetweenPlanes(float[] point0, float m, float a, float z0, float z, float c) {
		float[] point = new float[2];
		float ray = getRayProjection(m, z0, c);
		
		point[0] = (float) (ray * Math.cos(a)) + point0[0];
		point[1] = (float) (ray * Math.sin(a)) + point0[1];
		
		return point;
	}
	
	/**
	 * R = m z + c
	 * 
	 * @param m
	 * @param z
	 * @param c
	 * @return
	 */
	private float getRayProjection(float m, float z, float c) {
		return (m * z) + c;
	}
}
