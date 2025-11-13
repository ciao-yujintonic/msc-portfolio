from vec3 import Vec3
from ray import Ray

class Camera:
    def __init__(self):
        self.aspect_ratio = 16.0 / 9.0
        self.viewport_height = 2.0
        self.viewport_width = self.aspect_ratio * self.viewport_height
        self.focal_length = 1.0

        self.origin = Vec3(0, 0, 0)
        self.horizontal = Vec3(self.viewport_width, 0, 0)
        self.vertical = Vec3(0, self.viewport_height, 0)
        self.lower_left_corner = (
            self.origin
            - self.horizontal * 0.5
            - self.vertical * 0.5
            - Vec3(0, 0, self.focal_length)
        )

    def get_ray(self, u, v):
        return Ray(
            self.origin,
            self.lower_left_corner + self.horizontal*u + self.vertical*v - self.origin
        )
