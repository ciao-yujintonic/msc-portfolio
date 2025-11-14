from vec3 import Vec3
from ray import Ray

class Camera:
    def __init__(self, aspect_ratio):
        self.aspect_ratio = aspect_ratio
        # viewport is an image plane which is that camera looks at as a 2D screen in 3D space
        # and this decides of the field of view(FOV). Small viewport -> wide-angle, big viewport -> telephoto
        # FOV is decided by the height of the viewport (like the viewing angle of human eyes)
        self.viewport_height = 2.0
        # giving the same ratio as the image has to the width of the viewport not to distort the image
        self.viewport_width = self.aspect_ratio * self.viewport_height
        # distance from origin pin hole to viewport like the lens and sensor
        # small number : the screen is close to the camera. The more space to cover, the more ray spread out -> wide angle
        # big number : the screen is far from the camera. The less space to cover, the less ray spread out -> telephoto
        self.focal_length = 1.0

        # where the camera locates actually
        self.origin = Vec3(0, 0, 0)
        # horizontel vector which represents how far the width direction of the viewport is from the real space.
        self.horizontal = Vec3(self.viewport_width, 0, 0)
        # vertical vector which represents the vertical direction, and the magnitude
        self.vertical = Vec3(0, self.viewport_height, 0)
        # point of left-bottom corner of the viewport in the real space
        # from this, we calculate where the vieport is, how wide and how tall it is with direcrtions.
        # We can get any point on the viewport by adding horizontal and vertical vectors with some scale factor(u,v)
        # So move to the very left(horizontal), very bottom(vertical), and forward(focal length)
        # for example, (0,0,0) - (1.7778, 0,0) - (0, 2.0,0) - (0,0,1.0) = (-1.7778, -1 ,-1)
        self.lower_left_corner = (
            self.origin
            - self.horizontal / 2
            - self.vertical / 2
            # move the viewport forward along z axis by focal length (-z means moving forward) => viewport is in front of the camera
            - Vec3(0, 0, self.focal_length)
        )

    def get_ray(self, u, v):
        return Ray(
            self.origin,
            # from lower_left_corner, move u times horizontal and v times vertical to get the point on the viewport
            # it means that we calculate where the pixcel is located in the real world
            # for example, u=0.5, v=0.5 (center), horizontal*u = (3.5556, 0, 0) * 0.5 = (1.7778, 0, 0) / vertical*v = (0, 2.0, 0) * 0.5 = (0, 1, 0)
            # (-1.7778, -1, -1) + (1.7778,0,0) + (0,1,0) - (0,0,0) = (0,0,-1) => viewport center point which is 1 far away from the camera origin
            self.lower_left_corner + self.horizontal*u + self.vertical*v - self.origin
        )
