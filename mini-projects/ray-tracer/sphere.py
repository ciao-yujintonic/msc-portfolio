from hit import HitRecord

class Sphere:
    def __init__(self, center, radius, color):
        self.center = center
        self.radius = radius
        self.color = color

    def hit(self, ray, t_min, t_max):
        # Ray equation : ```P(t) = O * tD``` / Sphere equation : ```|P - C|^2 = R^2``` Ray-Sphere : ```|O + tD - C|^2 = R^2```
        # (O-C)·(O-C) + 2t (D·(O-C)) + t^2(D·D) - R^2 = 0 => at^2 + 2bt + c = 0
        # oc = O - C
        oc = ray.origin - self.center
        # a = D·D
        a = ray.direction.dot(ray.direction)
        # b = D·oc
        # it's supposed to be 2(oc·D), but we can just divide 2 later in the quadratic formula
        b = oc.dot(ray.direction)
        # c = oc·oc - R^2
        c = oc.dot(oc) - self.radius * self.radius
        # discriminant = b^2 -4ac when ax^2 +bx + c = 0, 2b^2 -4ac -> b^2 -ac (since we divided 2 already)
        discriminant = b*b - a*c

        if discriminant < 0:
            return None

        # x = (-b ± √(b^2 - 4ac)) /2a originally
        # t =(-b ± √(b^2 - ac)) / a
        sqrt_d = discriminant**0.5
        # near hit
        root = (-b - sqrt_d) / a

        if root < t_min or root > t_max:
            # far hit
            root = (-b + sqrt_d) / a
            # out of range
            if root < t_min or root > t_max:
                return None

        p = ray.at(root)
        # normal direction = P - C = radius always
        # normal for shading should be normalized vector (unit_vector = vector / |vector|) e.g. Law of Lambert, reflection, refraction
        # but why normal should be made into a unit vector?
        # if the magnitude of normal is not 1 in dot product, lighting intensity is distorted, reflection vector calculations are broken.
        # inverse radius for efficiency
        normal = (p - self.center) * (1.0 / self.radius)

        return HitRecord(p, normal, root, self.color)
