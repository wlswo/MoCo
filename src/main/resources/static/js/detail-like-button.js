

const addLight = (x, y, z, i, s) => {
    const light = new THREE.PointLight(0xffffff, i);
    light.position.set(x, y, z);
    s.add(light);
};

document.querySelectorAll('.button-star').forEach((button) => {
    const width = 400;
    const height = 200;

    const canvas = button.querySelector("canvas");

    const renderer = new THREE.WebGLRenderer({
        canvas: canvas,
        context: canvas.getContext("webgl2"),
        antialias: true,
        alpha: true,
    });

    renderer.setSize(width, height);
    renderer.setPixelRatio(4);

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(45, width / height, 4, 100);

    camera.position.set(0, 0, 30);

    addLight(0, 1, 5, 0.2, scene);
    addLight(0, 2, 0, 0.3, scene);
    addLight(5, 0, 1, 0.2, scene);
    addLight(-5, 0, 1, 0.2, scene);
    addLight(3, 0, 5, 0.6, scene);

    scene.add(new THREE.AmbientLight(0xffffff));

    const loader = new THREE.GLTFLoader();

    loader.setCrossOrigin("anonymous");
    loader.setDRACOLoader(new THREE.DRACOLoader());

    loader.load(
        "https://assets.codepen.io/165585/star-default.glb",
        function (data) {
            const object = data.scene;
            object.position.set(0, -0.5, 0);
            scene.add(object);

            let scaleTween, rotateTweenXZ, rotateTweenY, rotateTweenZ;

            button.addEventListener("pointerenter", () => {
                if (button.classList.contains("active")) {
                    return;
                }

                scaleTween = gsap.to(button, {
                    "--button-star-scale": 1,
                    ease: "elastic.out(1, .75)",
                    duration: 0.5,
                });
                gsap.to(button, {
                    "--button-star-greyscale": "0%",
                    "--button-star-hue": "0deg",
                    "--button-star-opacity": 1,
                    duration: 0.15,
                });

                rotateTweenXZ = gsap.to(object.rotation, {
                    duration: 0.25,
                    x: THREE.Math.degToRad(4),
                    z: THREE.Math.degToRad(12),
                    ease: "none",
                });

                rotateTweenY = gsap.to(object.rotation, {
                    duration: 1.5,
                    y: THREE.Math.degToRad(360),
                    ease: "none",
                    repeat: -1,
                });

                rotateTweenZ = gsap.to(object.rotation, {
                    duration: 4.5,
                    keyframes: [
                        {
                            ease: "none",
                            z: THREE.Math.degToRad(5),
                        },
                        {
                            ease: "none",
                            z: THREE.Math.degToRad(12),
                        },
                    ],
                    repeat: -1,
                });
            });

            button.addEventListener("pointerleave", () => {
                if (button.classList.contains("active")) {
                    return;
                }

                scaleTween.kill();
                rotateTweenXZ.kill();
                rotateTweenY.kill();
                rotateTweenZ.kill();
                gsap.to(button, {
                    "--button-star-scale": 0.75,
                    "--button-star-greyscale": "85%",
                    "--button-star-hue": "170deg",
                    "--button-star-opacity": 0.45,
                    duration: 0.2,
                });
                gsap.to(object.rotation, {
                    duration: 0.3,
                    x: THREE.Math.degToRad(0),
                    y: THREE.Math.degToRad(0),
                    z: THREE.Math.degToRad(0),
                });
            });

            button.addEventListener("click", () => {
                if (button.classList.contains("active")) {
                    gsap.to(button, {
                        "--button-label-x": "24px",
                        "--button-label-success-opacity": 0,
                        "--button-star-opacity": 0.45,
                        "--button-star-current-opacity": 1,
                        "--button-star-current-y": "0px",
                        "--button-star-new-opacity": 0,
                        "--button-star-new-y": "16px",
                        duration: 0.25,
                        clearProps: true,
                        onComplete() {
                            button.classList.remove("active");
                        },
                    });
                    return;
                }
                scaleTween.kill();
                rotateTweenY.kill();

                button.classList.add("active");

                gsap.to(button, {
                    "--button-star-current-opacity": 0,
                    "--button-star-current-y": "-16px",
                    "--button-star-new-opacity": 1,
                    "--button-star-new-y": "0px",
                    "--button-star-add-opacity": 1,
                    duration: 0.25,
                    delay: 0.5,
                });

                gsap.to(button, {
                    "--button-label-x": "0px",
                    "--button-label-success-opacity": 1,
                    duration: 0.25,
                    delay: 0.2,
                });

                gsap.to(button, {
                    "--button-star-add-opacity": 0,
                    duration: 0.2,
                    delay: 0.75,
                });

                gsap.to(button, {
                    "--button-star-add-y": "-8px",
                    duration: 0.5,
                    delay: 0.5,
                });

                gsap.to(button, {
                    "--button-star-scale": 1,
                    "--button-star-greyscale": "0%",
                    "--button-star-hue": "0deg",
                    "--button-star-opacity": 1,
                    duration: 0.15,
                });

                gsap.to(object.rotation, {
                    duration: 0.4,
                    y: THREE.Math.degToRad(0),
                });

                gsap.to(object.position, {
                    duration: 0.7,
                    motionPath: {
                        path: [
                            {
                                x: 0,
                                y: -0.5,
                            },
                            {
                                x: 5.45,
                                y: -5,
                            },
                            {
                                x: 10.9,
                                y: -0.5,
                            },
                            {
                                x: 7,
                                y: 7,
                            },
                        ],
                        curviness: 1.2,
                    },
                    ease: "sine.in",
                });

                gsap.to(button, {
                    "--button-star-opacity": 0,
                    duration: 0.1,
                    delay: 0.6,
                    onComplete() {
                        object.rotation.x = THREE.Math.degToRad(0);
                        object.rotation.y = THREE.Math.degToRad(0);
                        object.rotation.z = THREE.Math.degToRad(0);
                        object.position.set(0, -0.5, 0);
                        gsap.set(button, {
                            "--button-star-scale": 0.75,
                            "--button-star-greyscale": "85%",
                            "--button-star-hue": "170deg",
                            "--button-star-opacity": 0,
                        });
                    },
                });
            });
        }
    );

    const render = () => {
        requestAnimationFrame(render);

        renderer.render(scene, camera);
    };

    render();
});