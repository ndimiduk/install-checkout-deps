(ns leiningen.hooks.install-checkout-deps
  (:use [robert.hooke :only [add-hook]]
        [clojure.java.io :only [file]]
        [leiningen.core :only [read-project]]
        [leiningen.install :only [install]])
  (:require leiningen.deps))

(defn install-checkout-deps [f project & args]
  (doseq [dep (.listFiles (file (:root project) "checkouts"))]
    (install (read-project (.getAbsolutePath (file dep "project.clj")))))
  (apply f project args))

(add-hook #'leiningen.deps/deps install-checkout-deps)
