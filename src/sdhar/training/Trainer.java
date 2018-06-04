package sdhar.training;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

public class Trainer {

    public Trainer() {
        Tensor<Integer> i = Tensors.create(10);
        try (final SavedModelBundle bundle = SavedModelBundle.load("/Users/student/Projects/models/model.pb", "serve")) {
            Session session = bundle.session();
            System.out.println(session.runner().fetch("decode_layer_2:0"));
        }
    }

}
