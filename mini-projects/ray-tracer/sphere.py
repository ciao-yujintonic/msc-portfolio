from hit import HitRecord

class Sphere:
    def __init__(self, center, radius, color):
        self.center = center
        self.radius = radius
        self.color = color

    def hit(self, ray, t_min, t_max):
        oc = ray.origin - self.center
        a = ray.direction.dot(ray.direction)
        b = oc.dot(ray.direction)
        c = oc.dot(oc) - self.radius * self.radius
        discriminant = b*b - a*c

        if discriminant < 0:
            return None

        sqrt_d = discriminant**0.5
        root = (-b - sqrt_d) / a

        if root < t_min or root > t_max:
            root = (-b + sqrt_d) / a
            if root < t_min or root > t_max:
                return None

        p = ray.at(root)
        normal = (p - self.center) * (1.0 / self.radius)

        return HitRecord(p, normal, root, self.color)
