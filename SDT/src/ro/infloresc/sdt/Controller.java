package ro.infloresc.sdt;

import android.widget.TextView;

public class Controller {
	private int MAX_INPUTS = 2;
	private int MAX_OUTPUTS = 1;
	private int NO_ELEMENT = 0xffffff00;
	MainActivity view;
	Queue inputs;
	Queue outputs;
	
	Controller() {
		inputs = new Queue(MAX_INPUTS);
		outputs = new Queue(MAX_OUTPUTS);
	}

	Controller(MainActivity view) {
		inputs = new Queue(MAX_INPUTS);
		outputs = new Queue(MAX_OUTPUTS);
		this.view = view;
	}

	void onChange(int id) {
		// add the id
		// if the element is already in the list we don't do anything
		// if the element does not exist we check
		if (!inputs.offer(id)) {
			// the element was added to the input list
			// case one: it was clicked the first time
			if (inputs.getSize() == 2) {

			}
			// case two: it was an output that was clicked, so add it to the
			// output queue
			if (inputs.getSize() == MAX_INPUTS + 1) {
				// remove the old output
				outputs.poll();
				// add the oldest input to the output queue
				outputs.offer(inputs.poll());
			}
			//updateLabels();
		}
	}
	
	void print(){
		int i;
		System.out.print("Inputs are: ");
		for(i = 0; i < inputs.getSize(); i++) System.out.print(inputs.peek(i) + " ");
		System.out.print("Outputs are: ");
		for(i = 0; i < outputs.getSize(); i++) System.out.print(outputs.peek(i) + " ");
		System.out.println();
	}

	private void updateLabels() {
		updateForQueue(inputs, "Input");
		updateForQueue(outputs, "Output");
	}

	private void updateForQueue(Queue queue, String text) {
		int i, queueEnd = queue.getSize();
		for (i = 0; i < queueEnd; i++) {
			TextView label = (TextView) view.findViewById(queue.peek(i));
			if (!text.equals(label.getText()))
				label.setText(text);
		}
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.onChange(1);
		controller.print();
		controller.onChange(2);
		controller.print();
		controller.onChange(3);
		controller.print();
	}

	class Queue {
		int[] queue;
		int lastElementPosition;

		Queue(int nrOfElements) {
			lastElementPosition = -1;
			queue = new int[nrOfElements];
		}

		/*
		 * adds an element to the queue returns true on success returns false if
		 * we already have the object in the queue, and moves that object on the
		 * last position
		 */
		boolean offer(int id) {
			for (int i = 0; i <= lastElementPosition; i++) {
				if (queue[i] == id) {
					for (int j = i; j < lastElementPosition; j++) {
						queue[j] = queue[j + 1];
					}
					queue[lastElementPosition] = id;
					return false;
				}
			}
			queue[++lastElementPosition] = id;
			return true;
		}

		/*
		 * removes an element returns the removed element
		 */
		int poll() {
			if (lastElementPosition >= 0) {
				return queue[lastElementPosition--];
			}
			return NO_ELEMENT;
		}

		int getSize() {
			return lastElementPosition + 1;
		}

		int peek(int position) {
			return queue[position];
		}
	}
}
