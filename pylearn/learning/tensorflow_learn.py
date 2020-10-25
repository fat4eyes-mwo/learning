'''
Created on 24 Oct 2020

@author: Neil
'''
# TensorFlow and tf.keras

import tensorflow as tf
mnist = tf.keras.datasets.mnist
import sys

(x_train, y_train),(x_test, y_test) = mnist.load_data()

print((x_train, x_test))
print("x_train bytes: %d" % sys.getsizeof(x_train))
print("y_train bytes: %d" % sys.getsizeof(y_train))

x_train, x_test = x_train / 255.0, x_test / 255.0

model = tf.keras.models.Sequential([
  tf.keras.layers.Flatten(input_shape=(28, 28)),
  tf.keras.layers.Dense(128, activation='relu'),
  tf.keras.layers.Dropout(0.2),
  tf.keras.layers.Dense(10, activation='softmax')
])

model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy']) #could be causing a warning because of bug https://github.com/tensorflow/tensorflow/issues/42045

model.fit(x_train, y_train, epochs=5)

print("x_test bytes %d" % sys.getsizeof(x_test))
print("y_test bytes %d" % sys.getsizeof(y_test))
model.evaluate(x_test, y_test)

print("Prediction : %s" % model.predict(x_test))

tf.saved_model.save(model, "mnist.model")
tf.keras.models.save_model(model, "mnist.model2");
print("model saved")

newmodel = tf.keras.models.load_model("mnist.model")
newmodel.compile(optimizer='adam',
               loss='sparse_categorical_crossentropy',
               metrics=['accuracy'])

#newmodel2 = tf.saved_model.load("mnist.model2") 

#NOTE: Model MUST be compiled after loading (for possible cause see https://stackoverflow.com/questions/51252555/tf-keras-models-save-model-and-optimizer-warning)
print("model loaded")

newmodel.evaluate(x_test, y_test)
#newmodel2.evaluate(x_test, y_test) #note, is not a keras object, does not have evaluate method

