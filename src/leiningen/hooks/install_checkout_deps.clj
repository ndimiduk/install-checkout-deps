(ns leiningen.hooks.install-checkout-deps
  (:use [robert.hooke :only [add-hook]]
        [clojure.java.io :only [file]]
        [leiningen.core :only [read-project]]
        [leiningen.install :only [install]])
  (:require leiningen.deps))

(defn install-checkout-deps [f project & args]
  (doseq [dep (.listFiles (file (:root project) "checkouts"))]
    (let [fn (.getAbsolutePath (file dep "project.clj"))
          p (read-project fn)]
      (println (str "reading project file " fn))
      (when p
        (install p))))
  (apply f project args))

(add-hook #'leiningen.deps/deps install-checkout-deps)
